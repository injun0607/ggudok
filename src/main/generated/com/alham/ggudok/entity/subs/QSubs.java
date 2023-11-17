package com.alham.ggudok.entity.subs;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubs is a Querydsl query type for Subs
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubs extends EntityPathBase<Subs> {

    private static final long serialVersionUID = 971406036L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubs subs = new QSubs("subs");

    public final com.alham.ggudok.entity.QImageResourceEntity _super = new com.alham.ggudok.entity.QImageResourceEntity(this);

    public final QCategory category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    //inherited
    public final StringPath icon = _super.icon;

    //inherited
    public final StringPath iconName = _super.iconName;

    //inherited
    public final StringPath image = _super.image;

    //inherited
    public final StringPath imageName = _super.imageName;

    public final StringPath info = createString("info");

    public final NumberPath<Integer> ratingAvg = createNumber("ratingAvg", Integer.class);

    public final NumberPath<Integer> recommendSort = createNumber("recommendSort", Integer.class);

    public final ListPath<com.alham.ggudok.entity.member.Review, com.alham.ggudok.entity.member.QReview> reviews = this.<com.alham.ggudok.entity.member.Review, com.alham.ggudok.entity.member.QReview>createList("reviews", com.alham.ggudok.entity.member.Review.class, com.alham.ggudok.entity.member.QReview.class, PathInits.DIRECT2);

    public final NumberPath<Long> subsId = createNumber("subsId", Long.class);

    public final StringPath subsName = createString("subsName");

    public final ListPath<SubsRank, QSubsRank> subsRanks = this.<SubsRank, QSubsRank>createList("subsRanks", SubsRank.class, QSubsRank.class, PathInits.DIRECT2);

    public final ListPath<SubsRelTag, QSubsRelTag> subsRelTags = this.<SubsRelTag, QSubsRelTag>createList("subsRelTags", SubsRelTag.class, QSubsRelTag.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public QSubs(String variable) {
        this(Subs.class, forVariable(variable), INITS);
    }

    public QSubs(Path<? extends Subs> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubs(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubs(PathMetadata metadata, PathInits inits) {
        this(Subs.class, metadata, inits);
    }

    public QSubs(Class<? extends Subs> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

