package wtf.the.spring.session.gcs;

import com.google.cloud.storage.Storage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;

import static java.util.Objects.requireNonNull;

@Configuration
public class GcsHttpSessionConfiguration extends SpringHttpSessionConfiguration implements ImportAware {

    private String bucket = null;

    @Override
    public void setImportMetadata(AnnotationMetadata meta) {
        var map = meta.getAnnotationAttributes(EnableGcsHttpSession.class.getName());
        var attrs = requireNonNull(AnnotationAttributes.fromMap(map));
        bucket = attrs.getString("bucket");
    }

    @Bean
    public SessionRepository<MapSession> sessionRepository(Storage storage) {
        return new GcsSessionRepository(storage.get(bucket));
    }
}
