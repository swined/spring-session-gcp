package wtf.the.spring.session.gcp;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;

import static java.util.Objects.requireNonNull;

public class GcpHttpSessionConfiguration extends SpringHttpSessionConfiguration implements ImportAware {

    private Bucket bucket = null;

    @Override
    public void setImportMetadata(AnnotationMetadata meta) {
        bucket = StorageOptions
            .getDefaultInstance()
            .getService()
            .get(AnnotationAttributes
                .fromMap(meta.getAnnotationAttributes(EnableGcpHttpSession.class.getName()))
                .getString("bucket")
            );
    }

    @Bean
    public SessionRepository<MapSession> sessionRepository() {
        return new GcpSessionRepository(requireNonNull(bucket));
    }
}
