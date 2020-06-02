package com.wyt.study;

import com.wyt.study.bean.User;
import com.wyt.study.config.SecurityUtil;
import com.wyt.study.service.UserService;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

    @Autowired
    private UserService userService;

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private SecurityUtil securityUtil;

    private static final String PROCESS_DEFINITION_KEY = "categorizeProcess";

    @Test
    public void test(){
        /*User user = userService.getById(1);
        System.out.println(user);*/

        //securityUtil.logInAs("system");

        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        for (ProcessDefinition processDefinition : processDefinitionPage.getContent()) {

            System.out.println(processDefinition.getName());
            System.out.println(processDefinition.getKey());
        }

        //processRuntime.processDefinition(PROCESS_DEFINITION_KEY);

    }
}
