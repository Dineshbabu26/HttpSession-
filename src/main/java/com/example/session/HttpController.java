package com.example.session;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HttpController {
	
	@Autowired
	private UserRepo us;
	
	@PostMapping("adduser")
	public String adduser(Model model,@ModelAttribute("user")UserSession user) {
	   us.save(user);
	 return "redirect:/login";
	}
	
	 @RequestMapping("/register")
	    public String homePage(HttpServletRequest request,Model model){
	        HttpSession session = request.getSession(false);
	        if(session!=null)
	        {
	            int userid = (Integer) session.getAttribute("userid");
	            UserSession fromdb =us.findById(userid);
	            model.addAttribute("user",fromdb);
	            return "welcome";
	        }
	        UserSession us=new UserSession();
	        model.addAttribute("user",us);
	        return "register";
	    }
	 
	 @RequestMapping("/login")
	    public String LoginPage(HttpServletRequest request,Model model)
	    {
	        HttpSession session = request.getSession(false);
	        if(session!=null)
	        {
	            int userid = (Integer) session.getAttribute("userid");
	            UserSession fromdb =us.findById(userid);
	            model.addAttribute("user",fromdb);
	            return "welcome";
	        }
	        UserSession user = new UserSession();
	        model.addAttribute("user",user);
	        return "login";
	    }
	
	 @RequestMapping("/checkUser")
     public String checkUser(HttpServletRequest request,@ModelAttribute("user") UserSession users, Model model)
     {
        System.out.println(users);
	    int id=users.getUserid();
	    String password=users.getPassword();
	    UserSession fromdb= us.checkLogin(id);
	    if(id==fromdb.getUserid() && password.equals(fromdb.getPassword())) {
	   
	    HttpSession session=request.getSession();
	    session.setAttribute("id", id);
	    model.addAttribute("user",fromdb);
	    return"welcome";
          }
	   
		  UserSession user = new UserSession();
          model.addAttribute("user",user); 
	    return"redirect:/login";
    }
	
	 @RequestMapping("/logout")

	    public String logout(HttpServletRequest request)

	    {

	    HttpSession session=request.getSession(false);

	    session.invalidate();

	    return"redirect:/login";

	    }
	
	
	 @RequestMapping("/deleteuser/{userid}")
	    public String deleteUser(@PathVariable int userid, HttpServletRequest request) {
	        HttpSession session = request.getSession(false);
	        if(session!=null) {
	            us.deleteUser(userid);

	            return "register";
	        }
	        return "redirect:/login";
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}