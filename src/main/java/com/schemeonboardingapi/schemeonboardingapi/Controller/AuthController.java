package com.schemeonboardingapi.schemeonboardingapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schemeonboardingapi.schemeonboardingapi.Services.CommonMethod;
import com.schemeonboardingapi.schemeonboardingapi.model.User;
import com.schemeonboardingapi.schemeonboardingapi.repository.*;
import com.schemeonboardingapi.schemeonboardingapi.security.JwtUtil;

import jakarta.validation.Valid;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "https://rohitondot.github.io")
@RestController
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	private CommonMethod CommonMethod;
	
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) throws NoSuchAlgorithmException {
		
		System.out.println("Email:"+request.getEmpemail());		
		Optional<User> userOptional = userRepository.findByEmpEmail(request.getEmpemail());
		System.out.println("userOptional:"+userOptional.toString());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			System.out.println("Email:"+user.toString());		
			 SecureRandom srnd = new SecureRandom();
			    int saltLen = srnd.nextInt(10000);
			    String strsalt = String.valueOf(saltLen);
			    System.out.println("Salt:"+strsalt.toString());
			    // Convert salt to hex
			    String hashsalt_hidden = generateSHA256Hash(strsalt);
			    // Convert salt to hex
			     hashsalt_hidden = generateSHA256Hash(hashsalt_hidden.trim().concat("NICCIN"));
			     
			    String storedpwd = user.getUserPassword().concat(hashsalt_hidden);
			    System.out.println("storedpwd"+user.getUserPassword());
			    System.out.println("storedpwdwith hash:"+storedpwd);
	            String hashpwd = generateSHA256Hash(storedpwd);
	            System.out.println("hashpwd hash:"+hashpwd);
	            String InputPassword = request.getUser_password() ;
	            

	            String hashedInputPasswordValue = hashPassword(InputPassword,hashsalt_hidden);
	            System.out.println("hashedInputPasswordValue hash:"+hashedInputPasswordValue);
			    // Hash the input password with the session salt
		     //   String hashedInputPassword = generateSHA256Hash(request.getUser_password() + hashsalt_hidden);

		        // Compare with stored hash
	            if (hashpwd.equalsIgnoreCase(hashedInputPasswordValue)) {
				// if (request.getUser_password().equals(user.getUserPassword())) {
				String token = jwtUtil.generateToken(user.getEmpEmail());
				return ResponseEntity.ok(new AuthResponse(token, user.getUserRole()));
			}
		}
		return ResponseEntity.status(401).body("Invalid email or password.");
	}
	@GetMapping("/getDocumentscheme")
	public ResponseEntity<?> getDocumentscheme(){
		
		String response ="";
		response=this.CommonMethod.getDocumentscheme();
		if(response.equals("")){

			return ResponseEntity.status(400).body("Service Error");
				}
		else {
		return ResponseEntity.status(200).body(response);
		}
		
	}
	@GetMapping("/getRequiredList")
	public ResponseEntity<?> getRequiredList(){
		
		String response ="";
		
		response=this.CommonMethod.getRequiredList();
		if(response.equals("")){

			return ResponseEntity.status(400).body("Service Error");
				}
		else {
		return ResponseEntity.status(200).body(response);
		}
		
	}
	
	@GetMapping("/getPrefilledList")
	public ResponseEntity<?> getPrefilledList(){
		
		String response ="";
		
		response=this.CommonMethod.getPrefilledList();
		if(response.equals("")){

			return ResponseEntity.status(400).body("Service Error");
				}
		else {
		return ResponseEntity.status(200).body(response);
		}
		
	}
	@GetMapping("/getAPIFeildList")
	public ResponseEntity<?> getAPIFeildList(){
		
		String response ="";
		
		response=this.CommonMethod.getAPIFeildList();
		if(response.equals("")){

			return ResponseEntity.status(400).body("Service Error");
				}
		else {
		return ResponseEntity.status(200).body(response);
		}
		
	}

	@PostMapping("/getComponnetList")
	public ResponseEntity<?> getComponnetList(@RequestBody  String  schemeid){
		
		String response ="";
		
		response=this.CommonMethod.getComponnetList(schemeid);
		if(response.equals("")){

			return ResponseEntity.status(400).body("Service Error");
				}
		else {
		return ResponseEntity.status(200).body(response);
		}
		
	}
	@PostMapping("/postSchemeSpec_info")
	public ResponseEntity<?> postSchemeSpec_info(@RequestBody  String  scheme_json){
		
		String response ="";
		
		response=this.CommonMethod.postSchemeSpec_info(scheme_json);
		if(response.equals("")){

			return ResponseEntity.status(400).body("Service Error");
				}
		else {
		return ResponseEntity.status(200).body(response);
		}
		
	}
	private String generateSHA256Hash(String input) {
	    try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] inputBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
	        return convertByteArrayToHexString(inputBytes);
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Error generating SHA-256 hash", e);
	    }
	}

	private String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }
	
	public  String hashPassword(String password, String hashKey) throws NoSuchAlgorithmException {
        // First SHA-256 hash of the password
		String hashedPassword = submitedPasswordgenerateSHA256Hash(password);

		// Concatenate hashed password with hashKey
		String combinedString = hashedPassword.trim() + hashKey.trim();

		// Second SHA-256 hash
		return submitedPasswordgenerateSHA256Hash(combinedString);
	}
	private String submitedPasswordgenerateSHA256Hash(String input) {
	    try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] inputBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
	        return bytesToHex(inputBytes);
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Error generating SHA-256 hash", e);
	    }
	}
	  private static String bytesToHex(byte[] bytes) {
	        StringBuilder hexString = new StringBuilder();
	        for (byte b : bytes) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    }
}
