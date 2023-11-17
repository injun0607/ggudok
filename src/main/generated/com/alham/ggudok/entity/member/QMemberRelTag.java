package com.alham.ggudok.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberRelTag is a Querydsl query type for MemberRelTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberRelTag extends EntityPathBase<MemberRelTag> {

    private static final long serialVersionUID = -611171179L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberRelTag memberRelTag = new QMemberRelTag("memberRelTag");

    public final NumberPath<Integer> favorCnt = createNumber("favorCnt", Integer.class);

    public final NumberPath<Integer> haveCnt = createNumber("haveCnt", Integer.class);

    public final BooleanPath isBasic = createBoolean("isBasic");

    public final QMember member;

    public final NumberPath<Long> mrtId = createNumber("mrtId", Long.class);

    public final com.alham.ggudok.entity.QTag tag;

    public final NumberPath<Integer> tagSort = createNumber("tagSort", Integer.class);

    public QMemberRelTag(String variable) {
        this(MemberRelTag.class, forVariable(variable), INITS);
    }

    public QMemberRelTag(Path<? extends MemberRelTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberRelTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberRelTag(PathMetadata metadata, PathInits inits) {
        this(MemberRelTag.class, metadata, inits);
    }

    public QMemberRelTag(Class<? extends MemberRelTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.tag = inits.isInitialized("tag") ? new com.alham.ggudok.entity.QTag(forProperty("tag")) : null;
    }

}

