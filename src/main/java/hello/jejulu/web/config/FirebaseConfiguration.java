package hello.jejulu.web.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class FirebaseConfiguration {

    @Value("${firebase.config}")
    private String configFile;

    @Value("${firebase.bucket}")
    private String bucket;

    @PostConstruct
    public void init(){
        try{
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(configFile)))
                    .setStorageBucket(bucket)
                    .build();

            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
                log.info("FirebaseApp has been initialized {}",firebaseApp);
            }
        } catch (IOException e){
            log.error(e.getMessage());
        }
    }
}
