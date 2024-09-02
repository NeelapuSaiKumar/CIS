package com.orchasp.cis.ContactInformationSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.orchasp.cis.ContactInformationSystem.entity.Role;
import com.orchasp.cis.ContactInformationSystem.entity.User;
import com.orchasp.cis.ContactInformationSystem.repository.UserRepository;
@Service
public class EmailService {

	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private UserRepository userrepo;
	
	public void sendMail(String toMail, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("skumarneelapu@gmail.com");
        simpleMailMessage.setTo(toMail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);
        System.out.println("MAIL SENT SUCCESSFULLY............");
    }

    public void notifyAdminOnUserRegistration(User user) {
        String subject = "New User Registration";
        String body = "A new user with email " + user.getEmail() + " has registered and is awaiting approval.";
        String role1="ADMIN";
        if(role1.equalsIgnoreCase(user.getRole().name()))
        {
         String adminEmail = user.getEmail(); 
        sendMail(adminEmail, subject, body); 
        }
    }

    public User notifyUserOnApproval(User user) {
        String subject = "Your Account Has Been Approved";
        String body = "Congratulations! Your account has been approved by the admin.";
        sendMail(user.getEmail(), subject, body);
        return user;
    }

    public User notifyUserOnRejection(User user) {
        String subject = "Your Account Has Been Rejected";
        String body = "Unfortunately, your account has been rejected by the admin.";
        sendMail(user.getEmail(), subject, body);
        return user;
    }
    
    public void gettingTheMail(User u) {
    	Optional<User> user1=userrepo.findById(u.getId());
        User user=user1.get();
        String role1="ADMIN";
        String role2="USER";
        if(role1.equalsIgnoreCase(user.getRole().name()))
          {
           String adminEmail = user.getEmail(); 
           sendMail(adminEmail, "New Registration", "Hello admin,\n"+"\tYOU HAVE REGISTERED AS ADMIN.");
          }
        else  if(role2.equalsIgnoreCase(user.getRole().name())) {
    	   String userEmail = user.getEmail();
    	   List<User>userlist=userrepo.findByRole(Role.ADMIN);
    	   for(User user3:userlist) {
    		   String adminEmail = user3.getEmail(); 
             sendMail(adminEmail, "New User Registration", "Hello admin,\n"+"\tNew User is just now registered with "+userEmail+". \nPlease review and approve the new user .");
    	   }
         
         sendMail(userEmail, "Registration Received", "Your registration is being reviewed and will be approved shortly.");
        }else {
     	   throw new IllegalArgumentException("SomeThing Went Wrong.........");
        }
     }

	public User notifyUserOnMakingAdmin(User user) {
		String subject = "Role Change";
        String body = "Hello user,\n"+"\t Congratulations! Your Role has been Changed as <Admin>.";
        sendMail(user.getEmail(), subject, body);
        return user;
	}

	public User notifyAdminOnMakingUser(User user) {
		String subject = "Role Change";
        String body = "Hello admin,\n"+"\t Unfortunately, Your Role has been Changed as <User> by one of the admins.";
        sendMail(user.getEmail(), subject, body);
        return user;
	}
   

	
}
