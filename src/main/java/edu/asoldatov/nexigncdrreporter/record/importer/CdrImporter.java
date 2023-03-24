package edu.asoldatov.nexigncdrreporter.record.importer;

import edu.asoldatov.nexigncdrreporter.record.model.Subscriber;

import java.util.Collection;

public interface CdrImporter {

    Collection<Subscriber> importInternal(String fileName);

}
