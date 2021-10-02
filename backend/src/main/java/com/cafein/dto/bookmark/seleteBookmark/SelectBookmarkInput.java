package com.cafein.dto.bookmark.seleteBookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SelectBookmarkInput {
	int userId; //유저 ID
    int page;
    int size;
}
