/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package enterprise.servlet_stateless_war;

import java.io.*;

import javax.ejb.EJB;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.naming.*;

import enterprise.servlet_stateless_ejb.*;

// Though it is perfectly fine to declare the dependency on the bean
// at the type level, it is not required for stateless session bean
// Hence the next two lines are commented and we rely on the
// container to inject the bean.
// @EJB(name="StatelessSession", beanInterface=StatelessSession.class)

public class Servlet2Stateless
    extends HttpServlet {

    // Using injection for Stateless session bean is still thread-safe since
    // the ejb container will route every request to different
    // bean instances. However, for Stateful session beans the
    // dependency on the bean must be declared at the type level

    @EJB
    private StatelessSession sless;

    public void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {

            out.println("<HTML> <HEAD> <TITLE> Servlet Output </TITLE> </HEAD> <BODY BGCOLOR=white>");
            out.println("<CENTER> <FONT size=+1> Servlet2Stateless:: Please enter your name </FONT> </CENTER> <p> ");
            out.println("<form method=\"POST\">");
            out.println("<TABLE>");
            out.println("<tr><td>Name: </td>");
            out.println("<td><input type=\"text\" name=\"name\"> </td>");
            out.println("</tr><tr><td></td>");
            out.println("<td><input type=\"submit\" name=\"sub\"> </td>");
            out.println("</tr>");
            out.println("</TABLE>");
            out.println("</form>");
            String val = req.getParameter("name");

            if ((val != null) && (val.trim().length() > 0)) {
                out
                        .println("<FONT size=+1 color=red> Greeting from StatelessSessionBean: </FONT> "
                                + sless.sayHello(val) + "<br>");
            }
            out.println("</BODY> </HTML> ");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("webclient servlet test failed");
            throw new ServletException(ex);
        }
    }

}
