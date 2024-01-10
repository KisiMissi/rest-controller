package org.kaoden.ws.homework.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.kaoden.ws.homework.model.EntryAssessment;
import org.kaoden.ws.homework.service.audit.SecurityAuditService;
import org.kaoden.ws.homework.service.audit.argument.CreateSecurityAuditArgument;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class SecurityAspect {

    private static final String EMPTY_INFO = "ip: null, user-agent: null";
    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR" };

    final SecurityAuditService service;

    @AfterReturning(
            pointcut = "@annotation(org.kaoden.ws.homework.annotation.SecurityAuditCreation))",
            returning = "entryAssessment"
    )
    private void addSecurityAudit(EntryAssessment entryAssessment) {
        service.create(CreateSecurityAuditArgument.builder()
                                                  .assessmentId(entryAssessment.getId())
                                                  .info(getRequestInfo())
                                                  .createdAt(LocalDateTime.now())
                                                  .build());
    }

    private String getRequestInfo() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return EMPTY_INFO;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String ip = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");

        return "ip: " + ip + ", user-agent: " + userAgent;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ! ip.isBlank() && ! "unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}
