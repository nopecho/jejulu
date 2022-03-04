package hello.jejulu.web.controller.host;

import hello.jejulu.service.host.HostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/hosts")
public class HostController {

    private final HostService hostService;

}
