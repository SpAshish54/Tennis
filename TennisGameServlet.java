package com.learning.hello;


import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.learning.hello.model.TennisGameModel;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tennis")
public class TennisGameServlet extends HttpServlet {

    private static final long serialVersionUID = 956140707118987401L;
    private TennisGameModel controller;
    private JakartaServletWebApplication application;
    private TemplateEngine templateEngine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        controller = TennisGameModel.get(); // Assuming you have a TennisGameModel class
        application = JakartaServletWebApplication.buildApplication(getServletContext());
        // Initialize the game state and other necessary components
        // ...
        final WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve data from the TennisGameModel
        int scorePlayer1 = controller.getScorePlayer1();
        int scorePlayer2 = controller.getScorePlayer2();
        int setPlayer1 = controller.getSetPlayer1();
        int setPlayer2 = controller.getSetPlayer2();
        int matchPlayer1 = controller.getMatchPlayer1();
        int matchPlayer2 = controller.getMatchPlayer2();
        
        final IWebExchange webExchange = this.application.buildExchange(req, resp);
        final WebContext ctx = new WebContext(webExchange);
        
        ctx.setVariable("scorePlayer1", scorePlayer1);
        ctx.setVariable("scorePlayer2", scorePlayer2);
        ctx.setVariable("setPlayer1", setPlayer1);
        ctx.setVariable("setPlayer2", setPlayer2);
        ctx.setVariable("matchPlayer1", matchPlayer1);
        ctx.setVariable("matchPlayer2", matchPlayer2);
        ctx.setVariable("scoreNames", new String[]{"0", "15", "30", "40"});
        
        templateEngine.process("tennis", ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerParam = req.getParameter("player");
        
        if (playerParam != null) {
            int player = Integer.parseInt(playerParam);
            
            // Update game state and perform logic based on the player's action
            controller.updateGameState(player); // Update game state in the TennisGameModel
        }
        
        // Update the TennisGameModel and game logic
        controller.updateGameLogic(); // Update game logic in the TennisGameModel
        
        // Redirect to the doGet method to update the view
        doGet(req, resp);
    }

}
