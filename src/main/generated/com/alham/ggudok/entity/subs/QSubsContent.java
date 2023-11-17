package com.alham.ggudok.entity.subs;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubsContent is a Querydsl query type for SubsContent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubsContent extends EntityPathBase<SubsContent> {

    private static final long serialVersionUID = -383436027L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubsContent subsContent = new QSubsContent("subsContent");

    public final StringPath content = createString("content");

    public final NumberPath<Long> contentId = createNumber("contentId", Long.class);

    public final QSubsRank subsRank;

    public QSubsContent(String variable) {
        this(SubsContent.class, forVariable(variable), INITS);
    }

    public QSubsContent(Path<? extends SubsContent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubsContent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubsContent(PathMetadata metadata, PathInits inits) {
        this(SubsContent.class, metadata, inits);
    }

    public QSubsContent(Class<? extends SubsContent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.subsRank = inits.isInitialized("subsRank") ? new QSubsRank(forProperty("subsRank"), inits.get("subsRank")) : null;
    }

}

