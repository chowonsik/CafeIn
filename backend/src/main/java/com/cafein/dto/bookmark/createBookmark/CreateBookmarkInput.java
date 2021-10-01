package com.cafein.dto.bookmark.createBookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateBookmarkInput {
	int cafeId; //찜할 카페 ID
}
