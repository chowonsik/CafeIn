package com.cafein.dto.bookmark.deleteBookmark;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DeleteBookmarkInput {
	int cafeId; //찜 삭제할 카페 ID
}
