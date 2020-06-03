package com.companyd.hompage.seoul.controller;

import com.companyd.hompage.seoul.entity.LoginResponseData;
import com.companyd.hompage.seoul.entity.SignUpResponseData;
import com.companyd.hompage.seoul.entity.Users;
import com.companyd.hompage.seoul.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class MainController {
    @Autowired
    UserService service;

    public boolean isMatch(String password, String hashed){
        System.out.println("password: "+ password+ " hashed: "+hashed);
        System.out.println("isMatch 메서드 checkpw(): "+ BCrypt.checkpw(password,hashed)); // true || false
        return BCrypt.checkpw(password,hashed);
    }

    // 메인페이지
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String dispSignup() {

        return "/userRegister";
    }

//    //  회원가입
//    @PostMapping("/user/signup")
//    public Users createUser(@Valid @RequestBody Users user){
//        System.out.println("회원 가입 정보 등록");
//        SignUpResponseData res = new SignUpResponseData();
//        String hashPassword = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
//        user.setPassword(hashPassword); //암호화 저장
//        int createdUser = service.createUser(user);
//
//        if(createdUser >= 1){ // xml파일에다 id값 return받기로함
//            res.setIsSucceed(1);
//        }else{
//            res.setIsSucceed(0);
//        }
//        Users GetUser = service.getUserById(createdUser);
//        return GetUser;
//    }

    @PostMapping("/user/signup")
    public ModelAndView createUser(@Valid Users user){
        ModelAndView mav = new ModelAndView("/index");

        System.out.println("회원 가입 정보 등록");
        SignUpResponseData res = new SignUpResponseData();
        String hashPassword = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
        user.setPassword(hashPassword); //암호화 저장
        int createdUser = service.createUser(user);

        if(createdUser >= 1){ // xml파일에다 id값 return받기로함
            res.setIsSucceed(1);
        }else{
            res.setIsSucceed(0);
        }
        Users GetUser = service.getUserById(createdUser);
        return mav;
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "/userLogin";
    }

    @PostMapping("/user/login")
    public ModelAndView getLogin(@Valid Users user) {
        ModelAndView mav = new ModelAndView("/index");

        Users login = service.getLogin(user);
        LoginResponseData res = new LoginResponseData();

        System.out.println("넣은 비밀번호 : " + user.getPassword());
        System.out.println("가져온 비밀번호 : " + login.getPassword());
        if (isMatch(user.getPassword(),login.getPassword())) {
            res.setIsSucceed(1);
            System.out.println("로그인 성공");
        } else if (!isMatch(user.getPassword(),login.getPassword())) {
            res.setIsSucceed(0);
            System.out.println("비번이 서로 달라 로그인 실패");
            mav.setViewName("/userLogin");
        } else {
            mav.setViewName("/userLogin");
        }
        return mav;
    }

    // 마이 페이지 -> 요청 시 로그인한 정보를 바탕으로 화면에 뿌려줘야 함
    @GetMapping("/mypage")
    public String dispMypage() {
        return "/mypage";
    }

    // 마이 페이지 업로드 -> 요청 시 로그인한 정보를 바탕으로 화면에 뿌려줘야 함
    @GetMapping("/mypageUpload")
    public String dispMypageUpload() {
        return "/mypageUpload";
    }

    // 마이 페이지 업로드 post로 보내기
//    @PostMapping("/mypageUpload/upload")
//    public String upload(@RequestParam("file") MultipartFile multipartFile) {
//        log.info("upload");
//        File targetFile = new File(path.resolve(multipartFile.getOriginalFilename()).toString());
//        try {
//            InputStream fileStream = multipartFile.getInputStream();
//            FileUtils.copyInputStreamToFile(fileStream, targetFile);
//        } catch (IOException e) {
//            FileUtils.deleteQuietly(targetFile);
//            log.error("Failed to upload ", e);
//        }
//
//        return "redirect:/mypageUpload";
//    }

    // 마이 페이지 처리이력 -> 요청 시 로그인한 정보를 바탕으로 화면에 뿌려줘야 함
    @GetMapping("/mypageResultLog")
    public String dispMypageResultLog() {
        return "/mypageResultLog";
    }

    // 마이 페이지 다운로드 -> 요청 시 로그인한 정보를 바탕으로 화면에 뿌려줘야 함
    @GetMapping("/mypageDownload")
    public String dispMypageDownload() {
        return "/mypageDownload";
    }



}
