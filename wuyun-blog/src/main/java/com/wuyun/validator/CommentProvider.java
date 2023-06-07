package com.wuyun.validator;

import com.wuyun.model.dto.CommentDTO;
import com.wuyun.validator.groups.ArticleTalk;
import com.wuyun.validator.groups.Link;
import com.wuyun.validator.groups.ParentIdNotNull;
import com.wuyun.validator.groups.ParentIdNull;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.wuyun.enums.CommentTypeEnum.*;


/**
 * 评论分组校验器
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
public class CommentProvider implements DefaultGroupSequenceProvider<CommentDTO> {

    @Override
    public List<Class<?>> getValidationGroups(CommentDTO commentDTO) {
        List<Class<?>> defaultGroupSequence = new ArrayList<>();
        defaultGroupSequence.add(CommentDTO.class);
        if (commentDTO != null) {
            if (commentDTO.getCommentType().equals(ARTICLE.getType()) || commentDTO.getCommentType().equals(TALK.getType())) {
                defaultGroupSequence.add(ArticleTalk.class);
            }
            if (commentDTO.getCommentType().equals(LINK.getType())) {
                defaultGroupSequence.add(Link.class);
            }
            if (Objects.isNull(commentDTO.getParentId())) {
                defaultGroupSequence.add(ParentIdNull.class);
            }
            if (Objects.nonNull(commentDTO.getParentId())) {
                defaultGroupSequence.add(ParentIdNotNull.class);
            }
        }
        return defaultGroupSequence;
    }
}