package com.QuinchApp.Error;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
    ModelAndView errorPage = new ModelAndView();
    String errorMsg = "";
    int httpErrorCode = getErrorCode(httpRequest);
    switch (httpErrorCode) {
        case 400: {
            errorMsg = "El recurso solicitado no existe.";
            break;
        }
        case 403: {
            errorMsg = "No tiene permisos para acceder al recurso.";
            break;
        }
        case 401: {
            errorMsg = "No se encuentra autorizado.";
            break;
        }
        case 404: {
            errorMsg = "El recurso solicitado no fue encontrado.";
            errorPage.setViewName("404"); // devuelve directamente la página de error 404
            return errorPage;
        }
        case 500: {
            errorMsg = "Ocurrió un error interno.";
            break;
        }
    }
    errorPage.addObject("codigo", httpErrorCode);
    errorPage.addObject("mensaje", errorMsg);
    errorPage.setViewName("error");
    return errorPage;
}

    public String getErrorPath() {
        return "/error";
    }
}