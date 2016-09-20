package com.ccp.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.ccp.json.JsonResponse;
import com.ccp.model.User;
import com.ccp.model.Vehicle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping(value="/user")
public class UserController extends BaseController {
	
	@RequestMapping(value="/updatevehicle", method= {RequestMethod.POST}, consumes = { "application/json;charset=utf-8"},  produces = { "application/json;harset=utf-8" } )
	public String updateVehicle(@RequestBody Vehicle vehicle, BindingResult result, SessionStatus status,
			HttpServletRequest request) throws ParseException {
		
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		if(!this.requiredParamsForUpdatevehicle(vehicle)) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		
		vehicle.setObjectid(user.getObjectid());
		vehicle = this.vehicleService.save(vehicle);
		
		Gson gson = new GsonBuilder().create();
		return JsonResponse.getInstance().getSuccessMessage(gson.toJson(vehicle));
	}
	
	@Deprecated
	@RequestMapping(value="/updateprofile_v.0", method= {RequestMethod.POST}, consumes = { "application/json;charset=utf-8"},  produces = { "application/json;harset=utf-8" } )
	public String updateProfile(@RequestBody User userfromreq, BindingResult result, SessionStatus status,
			HttpServletRequest request) throws ParseException {
		
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		if(!this.requiredParamsForUpdateProfile(userfromreq)) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		
		user.setDob(userfromreq.getDob());
		user.setEmpid(userfromreq.getEmpid());
		user.setGender(userfromreq.getGender());
		user.setMobileNumber(userfromreq.getMobileNumber());
		user.setUseremail(userfromreq.getUseremail());
		user.setUserimage(userfromreq.getUserimage());
		user.setUsername(userfromreq.getUsername());
		
		user = this.userService.update(user);
		
		Gson gson = new GsonBuilder().setDateFormat(ConstantParams.dateInputFormat).create();
		return JsonResponse.getInstance().getSuccessMessage(gson.toJson(user));
	}
	
	@RequestMapping(value = "/uploadprofilepic", method=RequestMethod.POST)
	public String uploadprofilepic(@RequestParam("imagefile") MultipartFile file, @RequestParam("objectid") String objectid, 
			HttpServletRequest request) throws ParseException {
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		if(file.isEmpty() || objectid.isEmpty()) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		String fileName = null;
		if (!file.isEmpty()) {
            try {
                fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
               /* BufferedOutputStream buffStream = 
                        new BufferedOutputStream(new FileOutputStream(new File("D:/" + fileName)));
                buffStream.write(bytes);
                buffStream.close();*/
                user.setUserimage(bytes);
                user = this.userService.update(user);
        		
        		Gson gson = new GsonBuilder().setDateFormat(ConstantParams.dateInputFormat).create();
        		return JsonResponse.getInstance().getSuccessMessage(gson.toJson(user));
        		
            } catch (Exception e) {
                return "You failed to upload " + fileName + ": " + e.getMessage();
            }
        } else {
            return "Unable to upload. File is empty.";
        }
	}
	
	@RequestMapping(value = "/uploadencodeprofilepic", method=RequestMethod.POST)
	public String uploadprofilepic(@RequestParam("encodedimagefile") String encodedfile, @RequestParam("objectid") String objectid,
			HttpServletRequest request) throws ParseException {
		String fileName = null;
		User user = this.validateToken(request);
		if(user == null) {
			return JsonResponse.getInstance().getAuthErrorMessage();
		}
		
		if(encodedfile.isEmpty() || objectid.isEmpty()) {
			return JsonResponse.getInstance().getInsufficientMessage();
		}
		if (!encodedfile.isEmpty()) {
            try {
            	/*byte[] imageByte=Base64.decodeBase64(encodedfile);
            	 FileOutputStream imageOutFile = new FileOutputStream("D://"+objectid+".jpg");
                 imageOutFile.write(imageByte);
                 imageOutFile.close();*/

            	//return "You have successfully uploaded " + fileName;
                user.setUserimage(encodedfile.getBytes());
                user = this.userService.update(user);
        		
        		Gson gson = new GsonBuilder().setDateFormat(ConstantParams.dateInputFormat).create();
        		return JsonResponse.getInstance().getSuccessMessage(gson.toJson(user));
            } catch (Exception e) {
            	e.printStackTrace();
                return "You failed to upload " + fileName + ": " + e.getMessage();
            }
        } else {
            return "Unable to upload. File is empty.";
        }
	}
}
