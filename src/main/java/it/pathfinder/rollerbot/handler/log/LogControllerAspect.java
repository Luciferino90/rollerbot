package it.pathfinder.rollerbot.handler.log;

import java.util.Arrays;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;


/*
  Classe che gestisce tramite aspect e spring il logging delle api. Tutti i
  metodi del package it.arubapec.docflyapi.controller (da estendere)
  verranno intercettati e loggati.
 */
@Aspect
@Component
public class LogControllerAspect
{

    private static final Logger logger = LoggerFactory.getLogger(LogControllerAspect.class);

    private static final String STARTMETHOD = "txid: {}: **** START METHOD: {} ****";

    private static final String INPUTMETHOD_TEMPLATE = "txid: {}: {} {} ****";
    private static final String NOINPUTMETHOD_TEMPLATE = "txid: {}: {} - NO INPUT PARAMETERS ****";
    private static final String INPUTMETHOD_FILLER = "**** INPUT PARAMETERS:";

    private static final String ENDMETHOD = "txid: {}: **** END METHOD: {} ****";

    private static final String RETURNVALUE = "txid: {}: **** RETURN VALUE - {}: {} ****";
    private static final String NORETURNVALUE = "txid: {}: **** NO RETURN VALUE: {} ****";

    private static final String ENDMETHODWITHEXCEPTION = "txid: {}: **** END METHOD WITH EXCEPTION: {} ****";
    private static final String EXCEPTIONTYPE = "txid: {}: **** EXCEPTION TYPE FOR {}: {} ****";
    private static final String NOEXCEPTYIONTYPE = "txid: {}: **** NO EXCEPTION VALUE: {} ****";

    private static final String TOKENHOLDER = "Bearer ";

    /**
     * Inizializzato nel {@link #doBefore(JoinPoint joinPoint, RequestMapping requestMapping)}, tiene un UUID di thread.
     */
    private ThreadLocal<String> threadId = new ThreadLocal<>();

    /**
     * Punto di intervento che ci permette di stampare i log delle richieste e delle risposte.
     */
    @Pointcut("execution(* it.pathfinder.rollerbot.controller.TelegramBotWrapper.* (..))" +
            "&& @annotation(requestMapping)")
    private void logControllerPointcut(RequestMapping requestMapping)
    {
        // Non deve fare nulla, serve solo come punto di intervento per i metodi del package controller
    }

    /**
     * Punto di intervento che ci permette di stampare le eccezioni che avvengono a livello di dispatcher.
     * Non deve stampare nel log né le richieste né le risposte
     */
    @Pointcut("execution(* it.pathfinder.rollerbot.dispatcher.DispatcherService.* (..))")
    private void logDispatcherPointcut()
    {
        // Non deve fare nulla, serve solo come punto di intervento per i metodi del package dispatcher
    }

    /**
     * Attivato prima dell'esecuzione del metodo
     *
     * @param joinPoint
     *            Punto di intervento
     */
    @Before("logControllerPointcut(requestMapping)")
    public void doBefore(JoinPoint joinPoint, RequestMapping requestMapping)
    {
        String tid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        threadId.set(tid);
        logJpSign(requestMapping, joinPoint, STARTMETHOD);
        StringBuilder param = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            if (arg != null && ! arg.toString().startsWith(TOKENHOLDER)) {
                if (!StringUtils.isEmpty(param.toString())){
                    param.append(" - ");
                }
                param.append(arg.toString());
            }
        }
        String paramz = param.toString();
        if (!StringUtils.isEmpty(paramz)) {
            logger.info(INPUTMETHOD_TEMPLATE, tid, INPUTMETHOD_FILLER, paramz);
        } else {
            logger.info(NOINPUTMETHOD_TEMPLATE, tid, INPUTMETHOD_FILLER);
        }

    }

    /**
     * Attivato al ritorno dell'esecuzione del metodo, con corrispondente valore
     * ritornato
     *
     * @param joinPoint
     *            Punto di intervento
     * @param retVal
     *            valore riportato
     */
    @AfterReturning(pointcut = "logControllerPointcut(requestMapping)", returning = "retVal")
    public void doAfterReturn(JoinPoint joinPoint, RequestMapping requestMapping, Object retVal)
    {
        try {
            logJpSign(requestMapping, joinPoint, ENDMETHOD);
            String jsName = joinPoint.getSignature().getName();
            if (retVal != null) {
                logger.info(RETURNVALUE, threadId.get(), jsName, retVal);
            } else {
                logger.info(NORETURNVALUE, threadId.get(), jsName);
            }
        } finally {
            threadId.remove();
        }
    }

    /**
     * Intercettato in caso di eccezioni, le eccezioni si devono sollevare dal dispatcher
     * e devono poter risalire. Quindi verrà triggerato solamente dal punto di intervento
     * che ascolta il package dispatcher, altrimenti duplichiamo tutti i log.
     *
     * @param joinPoint
     *            Punto di intervento corrispondente
     * @param ex
     *            Eccezione
     */
    @AfterThrowing(pointcut = "logDispatcherPointcut()", throwing = "ex")
    public void doAfterThrowingDispatcher(JoinPoint joinPoint, Exception ex)
    {
        afterThrowing(joinPoint, ex);
    }

    /**
     * Metodo generico che gestisce le eccezioni, nel caso volessimo farle stampare da
     * molteplici punti ti intervento.
     *
     * @param joinPoint
     *            Punto di intervento corrispondente
     * @param ex
     *            Eccezione
     */
    private void afterThrowing(JoinPoint joinPoint, Exception ex)
    {
        logJpSign(null, joinPoint, ENDMETHODWITHEXCEPTION);
        String jsName = joinPoint.getSignature().getName();
        if (ex != null) {
            String errString = ex.toString();
            logger.info(EXCEPTIONTYPE, threadId.get(), jsName, errString);
        } else {
            logger.info(NOEXCEPTYIONTYPE, threadId.get(), jsName);
        }
    }

    /**
     * Micrometodo per il log iniziale. Logga con info la signature del
     * joinpoint e il threadid
     *
     * @param joinPoint
     *            joinpoint da considerare
     * @param format
     *            slf4j log format
     */
    private void logJpSign(RequestMapping requestMapping, JoinPoint joinPoint, String format)
    {
        String sigString = "";
        if (requestMapping != null && !StringUtils.isEmpty(requestMapping.value())) {
            sigString = Arrays.toString(requestMapping.value());
        }
        sigString += " - " + joinPoint.getSignature().toString();
        logger.info(format, threadId.get(), sigString);
    }

}