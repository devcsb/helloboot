package tobyspring.config;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyAutoConfigImportSelector implements DeferredImportSelector { // 사용자 구성 정보가 모두 로딩된 이후에, 자동 구성정보가 적용되기 위해서 DeferredImportSelector를 구현해야 함.
    private final ClassLoader classLoader;

    public MyAutoConfigImportSelector(ClassLoader classLoader) {
        this.classLoader = classLoader;

    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        List<String> autoConfigs = new ArrayList<>();

        ImportCandidates.load(MyAutoConfiguration.class, classLoader).forEach(autoConfigs::add);

        return autoConfigs.toArray(new String[0]);

        //방법 2
//        return Arrays.copyOf(autoConfigs.toArray(), autoConfigs.size(), String[].class);

        // 방법 3
//        return autoConfigs.stream().toArray(String[]::new);

        // 방법 4
//        Iterable<String> candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);
//        return StreamSupport.stream(candidates.spliterator(), false).toArray(String[]::new);
    }
}
