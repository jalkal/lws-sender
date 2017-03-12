package com.jalkal.apps.sender;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 12/03/2017.
 */
public class App extends NanoHTTPD {

    public App() throws IOException {
        super(8081);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning light weight server!  \n");
    }

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    @Override
    public Response serve(IHTTPSession session) {

        HashMap files = new HashMap();
        NanoHTTPD.Method method = session.getMethod();
        if(NanoHTTPD.Method.POST.equals(method)) {
            try {
                session.parseBody(files);
            } catch (IOException var5) {
                return newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, "text/plain", "SERVER INTERNAL ERROR: IOException: " + var5.getMessage());
            } catch (NanoHTTPD.ResponseException var6) {
                return newFixedLengthResponse(var6.getStatus(), "text/plain", var6.getMessage());
            }

            Map<String, String> parms = session.getParms();

            String name = parms.get("name");
            String email = parms.get("email");
            String phone = parms.get("phone");
            String message = parms.get("message");

            StringBuilder emailContent = new StringBuilder();

            emailContent.append("\n===== Mensaje recibido =======\n")
                    .append("Nombre: ").append(name).append("\n")
                    .append("Email: ").append(email).append("\n")
                    .append("Telefono: ").append(phone).append("\n")
                    .append("Mensaje: ").append(message).append("\n")
                    .append("-----------------------\n");

            System.out.print(emailContent.toString());
            return newFixedLengthResponse(emailContent.toString());
        }
        return newFixedLengthResponse("Not supported :(");

    }
}
