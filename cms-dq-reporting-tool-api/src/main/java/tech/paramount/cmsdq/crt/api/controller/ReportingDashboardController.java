package tech.paramount.cmsdq.crt.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ReportingDashboardController.MAPPING)
@RequiredArgsConstructor
public class ReportingDashboardController {
    public static final String MAPPING = "/api/v1/crt/dashboard";
}
