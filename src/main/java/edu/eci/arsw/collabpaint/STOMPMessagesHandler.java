/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabpaint;

import edu.eci.arsw.collabpaint.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author JuanHerrera
 */
@Controller
public class STOMPMessagesHandler{
        @Autowired
	SimpMessagingTemplate msgt;
        int cont = 0;
        Point[] points = new Point[3];
	@MessageMapping("/newpoint.{numdibujo}")    
	public void handlePointEvent(Point pt,@DestinationVariable String numdibujo) throws Exception {
            System.out.println("Nuevo punto recibido en el servidor!:"+pt);
            msgt.convertAndSend("/topic/newpoint."+numdibujo, pt);
            points[cont] = pt;
            cont ++;
            if (cont == 3){
                msgt.convertAndSend("/topic/newpolygon."+numdibujo, points);
                cont = 0;
                points = new Point[3];
            }
	}
}
