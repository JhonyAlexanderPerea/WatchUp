package co.uniquindio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/reportHistories")
@RestController
public class ReportHistoryController {
    private final ReportHistoryService reportHistoryService;

}
