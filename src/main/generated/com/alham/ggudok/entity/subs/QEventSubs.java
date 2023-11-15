package com.alham.ggudok.entity.subs;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventSubs is a Querydsl query type for EventSubs
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventSubs extends EntityPathBase<EventSubs> {

    private static final long serialVersionUID = -2018451476L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventSubs eventSubs = new QEventSubs("eventSubs");

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> eventId = createNumber("eventId", Long.class);

    public final StringPath eventImage = createString("eventImage");

    public final StringPath info = createString("info");

    public final StringPath infoTag = createString("infoTag");

    public final BooleanPath isValid = createBoolean("isValid");

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final QSubs subs;

    public QEventSubs(String variable) {
        this(EventSubs.class, forVariable(variable), INITS);
    }

    public QEventSubs(Path<? extends EventSubs> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventSubs(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventSubs(PathMetadata metadata, PathInits inits) {
        this(EventSubs.class, metadata, inits);
    }

    public QEventSubs(Class<? extends EventSubs> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.subs = inits.isInitialized("subs") ? new QSubs(forProperty("subs"), inits.get("subs")) : null;
    }

}

