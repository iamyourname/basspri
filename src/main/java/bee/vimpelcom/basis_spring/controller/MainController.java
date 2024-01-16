package bee.vimpelcom.basis_spring.controller;


import bee.vimpelcom.basis_spring.entity.UserMail;
import bee.vimpelcom.basis_spring.zabbix.ZabbixAPI;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static bee.vimpelcom.basis_spring.zabbix.ZabbixAPI.table_inc;

@Controller
public class MainController {

    //final static Logger logger = Logger.getLogger(MainController.class);
    final static Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/d")
    public String index(Model model){
        model.addAttribute("message","hello world");
        model.addAttribute("userMail", new UserMail());
        return "index";
    }

    @PostMapping("/d")
    public String sendUserReport(@ModelAttribute UserMail userMail, Model model){
        model.addAttribute("userMail", userMail);
        String email = userMail.getEmail();

        logger.info(email);

        return "index";

    }
//
    @GetMapping("/")
    public String dashboardIn(Model model) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        ZabbixAPI.updateAuthToken();

        model.addAttribute("dateTime", LocalDateTime.now().format(dateTimeFormatter));
        model.addAttribute("percent_errors", ZabbixAPI.getProcErrors()); // процент ошибочных
        model.addAttribute("percent_success", ZabbixAPI.getProcSuccess()); // процент ошибочных
        model.addAttribute("success_count", ZabbixAPI.getSuccessActions()); // ecgtiyst
        model.addAttribute("zakupki_status", ZabbixAPI.getZakupkiStatus()); // ecgtiyst
        model.addAttribute("users_count", ZabbixAPI.getUsersCount()); // ecgtiyst
        model.addAttribute("max_users_count", ZabbixAPI.getMaxUsersCount()); // ecgtiyst

        model.addAttribute("all_inc",ZabbixAPI.getAllInc());
        model.addAttribute("incoming_inc",ZabbixAPI.getIncomingIncToday());
        model.addAttribute("done_inc",ZabbixAPI.getDoneIncToday());
        model.addAttribute("in_work_inc",ZabbixAPI.getInWorkInc());
        model.addAttribute("wait_inc",ZabbixAPI.getWaitIncToday());

        ZabbixAPI.getProsrokTable();

        model.addAttribute("expired_ind",table_inc);


        /*
        resultMes.append(getAllInc()+"<br>");
        resultMes.append(getIncomingIncToday()+"<br>");
        resultMes.append(getDoneIncToday()+"<br>");
        resultMes.append(getInWorkInc()+"<br>");
        //resultMes.append(getActiveIncToday()+"<br>");
        resultMes.append(getWaitIncToday()+"<hr>");
        resultMes.append("<h3>ПРОСРОЧЕННЫЕ ИНЦИДЕНТЫ</h3>");
        resultMes.append(getProsrokTable());
         */



        logger.info("Get dashboard");
        return "index";
    }


}
