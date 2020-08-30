package wtf.the.spring.session.gcs;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.SerializationUtils.deserialize;
import static org.springframework.util.SerializationUtils.serialize;

public class GcsSessionRepository implements SessionRepository<MapSession> {

    private final Bucket bucket;

    public GcsSessionRepository(Bucket bucket) {
        this.bucket = requireNonNull(bucket);
    }

    @Override
    public MapSession createSession() {
        return new MapSession();
    }

    @Override
    public void save(MapSession session) {
        bucket.getStorage().create(BlobInfo.newBuilder(BlobId.of(bucket.getName(), session.getId())).build(), serialize(session));
    }

    @Override
    public MapSession findById(String id) {
        return Optional.ofNullable(bucket.get(id)).map(blob -> (MapSession)deserialize(bucket.getStorage().readAllBytes(blob.getBlobId()))).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        bucket.get(id).delete();
    }
}
