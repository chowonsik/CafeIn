from parse import load_dataframes
import pandas as pd
import shutil


def sort_stores_by_score(dataframes, n=20, min_reviews=30):
    """
    Req. 1-2-1 각 음식점의 평균 평점을 계산하여 높은 평점의 음식점 순으로 `n`개의 음식점을 정렬하여 리턴합니다
    Req. 1-2-2 리뷰 개수가 `min_reviews` 미만인 음식점은 제외합니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    scores_group = stores_reviews.groupby(["store", "store_name"])
    scores = scores_group.mean().sort_values(by=["score"], ascending=False)
    scores = scores.loc[scores["review_cnt"] >= min_reviews]
    return scores.head(n=n).reset_index()


def get_most_reviewed_stores(dataframes, n=20):
    """
    Req. 1-2-3 가장 많은 리뷰를 받은 `n`개의 음식점을 정렬하여 리턴합니다
    """
    stores_reviews = dataframes["stores"]
    scores = stores_reviews.sort_values(by=["review_cnt"], ascending=False)

    return scores.head(n=n).reset_index()


def get_most_active_users(dataframes, n=20):
    """
    Req. 1-2-4 가장 많은 리뷰를 작성한 `n`명의 유저를 정렬하여 리턴합니다.
    """
    users_reviews = pd.merge(
        dataframes["users"], dataframes["reviews"], left_on="id", right_on="user"
    )
    users_group = users_reviews.groupby(["user"]).size().reset_index().rename(columns={0:'cnt'})
    users = users_group.sort_values(by=["cnt"], ascending=False)

    return users.head(n=n).reset_index()


def main():
    data = load_dataframes()

    term_w = shutil.get_terminal_size()[0] - 1
    separater = "-" * term_w

    stores_most_scored = sort_stores_by_score(data)
    most_reviewed_stores = get_most_reviewed_stores(data)
    most_active_users = get_most_active_users(data)

    print("[최고 평점 음식점]")
    print(f"{separater}\n")
    for i, store in stores_most_scored.iterrows():
        print(
            "{rank}위: {store}({score}점)".format(
                rank=i + 1, store=store.store_name, score=store.score
            )
        )
    print(f"\n{separater}\n\n")

    print("[가장 많은 리뷰를 받은 음식점]")
    print(f"{separater}\n")
    for i, store in most_reviewed_stores.iterrows():
        print(
            "{rank}위: {store}({review_cnt}개)".format(
                rank=i + 1, store=store.store_name, review_cnt=store.review_cnt
            )
        )
    print(f"\n{separater}\n\n")

    print("[가장 많은 리뷰쓴 유저]")
    print(f"{separater}\n")
    for i, user in most_active_users.iterrows():
        print(
            "{rank}위: {id}({count}개)".format(
                rank=i + 1, id=user.user, count=user.cnt
            )
        )
    print(f"\n{separater}\n\n")


if __name__ == "__main__":
    main()

#%%
data = load_dataframes()
df = data["stores"].head()

df_stores = data["stores"]
df_review = data["reviews"]
print(df_stores["area"])
print(df_review[df_review["user"] == 469245])

print(df_review.groupby("user")[["content"]].count().sort_values(by=["content"], ascending=False).head(20))

#%%
dataframes = load_dataframes()
stores_reviews = pd.merge(dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store")
scores_group = stores_reviews.groupby(["store", "store_name"])
scores = scores_group.mean().sort_values(by=["score"], ascending=False)


scores_group = stores_reviews.groupby(["store", "store_name"])
scores = scores_group.mean().sort_values(by=["score"], ascending=False)
scores = scores.loc[scores["review_cnt"] >= 30]

