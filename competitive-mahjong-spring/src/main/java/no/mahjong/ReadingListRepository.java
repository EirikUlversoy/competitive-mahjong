package no.mahjong;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Eirik on 30.12.2016.
 */
    public interface ReadingListRepository extends JpaRepository<Book, Long> {
        List<Book> findByReader(String reader);
    }


