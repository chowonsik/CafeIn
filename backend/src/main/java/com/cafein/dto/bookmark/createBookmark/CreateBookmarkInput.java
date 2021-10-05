package com.cafein.dto.bookmark.createBookmark;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateBookmarkInput {
	int cafeId; //찜할 카페 ID
}
