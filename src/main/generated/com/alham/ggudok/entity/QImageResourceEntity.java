package com.alham.ggudok.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QImageResourceEntity is a Querydsl query type for ImageResourceEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QImageResourceEntity extends EntityPathBase<ImageResourceEntity> {

    private static final long serialVersionUID = -46390830L;

    public static final QImageResourceEntity imageResourceEntity = new QImageResourceEntity("imageResourceEntity");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final StringPath icon = createString("icon");

    public final StringPath iconName = createString("iconName");

    public final StringPath image = createString("image");

    public final StringPath imageName = createString("imageName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public QImageResourceEntity(String variable) {
        super(ImageResourceEntity.class, forVariable(variable));
    }

    public QImageResourceEntity(Path<? extends ImageResourceEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImageResourceEntity(PathMetadata metadata) {
        super(ImageResourceEntity.class, metadata);
    }

}

