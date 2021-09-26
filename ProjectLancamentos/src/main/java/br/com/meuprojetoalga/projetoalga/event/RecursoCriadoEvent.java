package br.com.meuprojetoalga.projetoalga.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;
import java.time.Clock;

@Getter
public class RecursoCriadoEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Long codigo;

    public RecursoCriadoEvent(Object source,HttpServletResponse response,Long codigo) {
        super(source);
        this.response = response;
        this.codigo = codigo;

    }

}
