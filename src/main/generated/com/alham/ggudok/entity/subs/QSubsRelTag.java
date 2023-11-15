package com.alham.ggudok.entity.subs;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubsRelTag is a Querydsl query type for SubsRelTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubsRelTag extends EntityPathBase<SubsRelTag> {

    private static final long serialVersionUID = -839183307L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubsRelTag subsRelTag = new QSubsRelTag("subsRelTag");

    public final NumberPath<Long> srtId = createNumber("srtId", Long.class);

    public final QSubs subs;

    public final com.alham.ggudok.entity.QTag tag;

    public QSubsRelTag(String variable) {
        this(SubsRelTag.class, forVariable(variable), INITS);
    }

    public QSubsRelTag(Path<? extends SubsRelTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubsRelTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubsRelTag(PathMetadata metadata, PathInits inits) {
        this(SubsRelTag.class, metadata, inits);
    }

    public QSubsRelTag(Class<? extends SubsRelTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.subs = inits.isInitialized("subs") ? new QSubs(forProperty("subs"), inits.get("subs")) : null;
        this.tag = inits.isInitialized("tag") ? new com.alham.ggudok.entity.QTag(forProperty("tag")) : null;
    }

}

