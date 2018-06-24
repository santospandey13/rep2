package health.my.santosh.healthinsurance;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Santosh on 1/24/2018.
 */
public class SendMail extends AsyncTask<Void,Void,Void>
{


    private Context context;
    private Session session;

    //Information to send email
    private String toto;
    private String subject;
    private String message;


    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    public SendMail(Main2Activity context, String subject, String message, String toto){
        //Initializing variables
        this.context = context;
        this.toto = toto;
        this.subject = subject;
        this.message = message;

    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Sending Email","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
        Toast.makeText(context,"Email Sent", Toast.LENGTH_LONG).show();
    }




    @Override
    protected Void doInBackground(Void... params) {
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(Config.EMAIL));
            //Adding receiver


           // String[] sentto ={"santospandey13@gmail.com","santosh.pandey47@gmail.com"};
            String[] sentto =toto.split(";");
            InternetAddress[] newaddress =new InternetAddress[sentto.length];
            int counter=0;
            for(String recipient :sentto)
            {
                newaddress[counter] =new InternetAddress(recipient.trim());
                counter++;
            }
            mm.setRecipients(Message.RecipientType.TO,newaddress);

            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(message);

            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(message);

            String attachmentPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HEALTH INSURANCE QUOTE";
            String attachmentName = "Health Insurance.PDF";



            File att = new File(new File(attachmentPath), attachmentName);
            DataSource source = new FileDataSource(att);
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(attachmentName);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            mm.setContent(multipart );

            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
    }

