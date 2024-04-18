package com.adobe.aem.guides.wknd.core.servlets;

import javax.jcr.Node;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.jcr.RepositoryException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/*@Component(
        service = Servlet.class,
        immediate = true,
        property = {
                "sling.servlet.methods=GET",
                "sling.servlet.methods=POST",
                "sling.servlet.resourceTypes=myproject/components/content/mycomponent",
                "sling.servlet.extensions=html"
        }
)*/
@Component(service = { Servlet.class })
@SlingServletResourceTypes(
        resourceTypes = {"wknd/components/helloworld","wknd/path"},
        methods = {HttpConstants.METHOD_GET, HttpConstants.METHOD_POST},
        selectors = "query",
        extensions = {"pdf","ppt"}
)
public class CustomServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String inputData = request.getParameter("inputData");
        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource resource = resourceResolver.getResource("/content/wknd/us/en/homepage/jcr:content/root/container/container/helloworld");
        Node node = resource.adaptTo(Node.class);
        try {
            node.setProperty("customProp",inputData);
            node.getSession().save();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String inputData = request.getParameter("inputData");
        response.getWriter().write("this is from post call - parameter passed is = "+inputData);
    }
}
