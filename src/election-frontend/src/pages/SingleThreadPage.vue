<template>
  <!-- Enkel Draad -->
  <div class="single-thread max-w-4xl mx-auto px-4 py-8 bg-gray-200 dark:bg-gray-800 rounded-lg shadow-md">
    <h1 class="text-3xl font-bold mb-6 text-gray-800 dark:text-gray-100 text-center">{{ thread.title }}</h1>

    <div v-if="loading" class="text-center text-gray-500 dark:text-gray-400">
      <p>Draad laden...</p>
    </div>

    <div v-if="!loading" class="thread-content flex justify-around items-center pt-4">
      <!-- Thread Inhoud -->
      <div class="thread-details">
        <h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-100">{{ thread.title }}</h2>
        <p class="text-gray-600 dark:text-gray-300 mb-4">{{ thread.body }}</p>
        <p class="text-sm text-gray-500 dark:text-gray-400 mb-2">
          Geplaatst door: <span class="font-medium text-gray-700 dark:text-gray-300">{{ thread.user.username }}</span>
        </p>
        <p class="text-sm text-gray-500 dark:text-gray-400">
          Geplaatst op: <span class="font-medium text-gray-700 dark:text-gray-300">{{ thread.date }}</span>
        </p>
      </div>

      <!-- Like/Dislike Sectie -->
      <div class="like-section flex flex-col items-center ml-6">
        <button
            :class="{ 'text-green-500': isLiked, 'text-gray-400': !isLiked }"
            @click="toggleLike"
            class="like-button transition duration-200"
        >
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-8">
            <path d="M7.493 18.5c-.425 0-.82-.236-.975-.632A7.48 7.48 0 0 1 6 15.125c0-1.75.599-3.358 1.602-4.634.151-.192.373-.309.6-.397.473-.183.89-.514 1.212-.924a9.042 9.042 0 0 1 2.861-2.4c.723-.384 1.35-.956 1.653-1.715a4.498 4.498 0 0 0 .322-1.672V2.75A.75.75 0 0 1 15 2a2.25 2.25 0 0 1 2.25 2.25c0 1.152-.26 2.243-.723 3.218-.266.558.107 1.282.725 1.282h3.126c1.026 0 1.945.694 2.054 1.715.045.422.068.85.068 1.285a11.95 11.95 0 0 1-2.649 7.521c-.388.482-.987.729-1.605.729H14.23c-.483 0-.964-.078-1.423-.23l-3.114-1.04a4.501 4.501 0 0 0-1.423-.23h-.777ZM2.331 10.727a11.969 11.969 0 0 0-.831 4.398 12 12 0 0 0 .52 3.507C2.28 19.482 3.105 20 3.994 20H4.9c.445 0 .72-.498.523-.898a8.963 8.963 0 0 1-.924-3.977c0-1.708.476-3.305 1.302-4.666.245-.403-.028-.959-.5-.959H4.25c-.832 0-1.612.453-1.918 1.227Z" />
          </svg>
        </button>

        <span class="text-gray-700 dark:text-gray-300 py-3 font-medium">{{ likeCount }}</span>
        <button
            :class="{ 'text-red-500': isDisliked, 'text-gray-400': !isDisliked }"
            @click="toggleDislike"
            class="dislike-button transition duration-200"
        >
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-8">
            <path d="M15.73 5.5h1.035A7.465 7.465 0 0 1 18 9.625a7.465 7.465 0 0 1-1.235 4.125h-.148c-.806 0-1.534.446-2.031 1.08a9.04 9.04 0 0 1-2.861 2.4c-.723.384-1.35.956-1.653 1.715a4.499 4.499 0 0 0-.322 1.672v.633A.75.75 0 0 1 9 22a2.25 2.25 0 0 1-2.25-2.25c0-1.152.26-2.243.723-3.218.266-.558-.107-1.282-.725-1.282H3.622c-1.026 0-1.945-.694-2.054-1.715A12.137 12.137 0 0 1 1.5 12.25c0-2.848.992-5.464 2.649-7.521C4.537 4.247 5.136 4 5.754 4H9.77a4.5 4.5 0 0 1 1.423.23l3.114 1.04a4.5 4.5 0 0 0 1.423.23ZM21.669 14.023c.536-1.362.831-2.845.831-4.398 0-1.22-.182-2.398-.52-3.507-.26-.85-1.084-1.368-1.973-1.368H19.1c-.445 0-.72.498-.523.898.591 1.2.924 2.55.924 3.977a8.958 8.958 0 0 1-1.302 4.666c-.245.403.028.959.5.959h1.053c.832 0 1.612-.453 1.918-1.227Z" />
          </svg>

        </button>
      </div>
    </div>

    <!-- Reacties op Draad -->
    <div class="comments mt-8">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-100">Reacties</h2>
      <div class="comment-list space-y-4">
        <div
            class="comment-item p-4 bg-gray-50 dark:bg-gray-700 border rounded-lg shadow-md"
            v-for="comment in comments"
            :key="comment.id"
        >
          <p class="text-gray-600 dark:text-gray-300">{{ comment.body }}</p>
          <p class="text-sm text-gray-400 dark:text-gray-400">
            Door: <span class="font-medium text-gray-700 dark:text-gray-300">{{ comment.user.username }}</span> op {{ comment.date }}
          </p>
        </div>
        <CreateCommentComponent/>
      </div>
    </div>

    <!-- Terug naar Draadjes Knop -->
    <div class="mt-6 text-center">
      <router-link to="/threads" class="px-6 py-3 bg-green-700 dark:bg-green-800 text-white rounded-2xl hover:bg-green-900 dark:hover:bg-green-700 transition duration-200">Terug naar Threads</router-link>
    </div>
  </div>
</template>

<script>
import { threadService } from "../services/ThreadService";
import { likeService } from "../services/LikeService";
import CreateCommentComponent from "../components/CreateComment.vue";

export default {
  name: "SingleThread",
  components: {
    CreateCommentComponent,
  },
  data() {
    return {
      thread: {},
      comments: [],
      loading: true,
      likeCount: 0,
      isLiked: false,
      isDisliked: false,
    };
  },
  methods: {
    async fetchThread() {
      try {
        this.thread = await threadService.fetchThreadData(this.$route.params.id);
        await this.fetchLikeCount();
        await this.fetchUserVote();
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async fetchComments() {
      try {
        this.comments = await threadService.fetchComments(this.$route.params.id);
      } catch (err) {
        console.error(err);
      }
    },
    async fetchLikeCount() {
      try {
        if (!this.thread.id) {
          console.warn("Thread ID NOT FOUND");
          return;
        }
        const summary = await likeService.fetchVoteSummary(this.thread.id);

        this.likeCount = (summary.upvotes || 0) - (summary.downvotes || 0);
      } catch (err) {
        console.error("Error fetching like count:", err);
      }
    },
    async fetchUserVote() {
      try {
        const likes = await likeService.fetchLikes(this.thread.id);

        const userId = this.getUserIdFromToken();

        const userVote = likes.find((like) => {
          return like.userId === Number(userId) && like.threadId === Number(this.thread.id);
        });

        if (userVote) {
          this.isLiked = userVote.voteType === "UPVOTE";
          this.isDisliked = userVote.voteType === "DOWNVOTE";
        } else {
          this.isLiked = false;
          this.isDisliked = false;
        }
        console.log("Vote status - Liked:", this.isLiked, "Disliked:", this.isDisliked);
      } catch (err) {
        console.error("Error fetching user vote:", err);
      }
    },
    async toggleLike() {
      const userId = this.getUserIdFromToken();

      if (this.isLiked) {
        await this.removeVote("UPVOTE");
        this.isLiked = false;
      } else {
        if (this.isDisliked) {
          await this.removeVote("DOWNVOTE");
          this.isDisliked = false;
        }
        await this.addVote("UPVOTE");
        this.isLiked = true;
      }
      await this.fetchLikeCount();
    },
    async toggleDislike() {
      const userId = this.getUserIdFromToken();

      if (this.isDisliked) {
        await this.removeVote("DOWNVOTE");
        this.isDisliked = false;
      } else {
        if (this.isLiked) {
          await this.removeVote("UPVOTE");
          this.isLiked = false;
        }
        await this.addVote("DOWNVOTE");
        this.isDisliked = true;
      }
      await this.fetchLikeCount();
    },
    async addVote(voteType) {
      const userId = this.getUserIdFromToken();
      if (!userId) {
        console.warn("User ID not found, cannot add vote");
        return;
      }
      try {
        console.log(`Adding vote: ${voteType}, User ID: ${userId}`);
        await likeService.addVote(this.thread.id, voteType, Number(userId));
        await this.fetchLikeCount();
      } catch (err) {
        console.error("Error adding vote:", err);
      }
    },
    async removeVote(voteType) {
      const userId = this.getUserIdFromToken();
      if (!userId) {
        console.warn("User ID not found, cannot remove vote");
        return;
      }
      try {
        await likeService.removeVote(this.thread.id, voteType, Number(userId));
      } catch (err) {
        console.error("Error removing vote:", err);
      }
    },
    // Haal userId uit het token
    getUserIdFromToken() {
      const token = localStorage.getItem("token");
      if (token) {
        try {
          const userId = JSON.parse(atob(token.split(".")[1])).userId || null;
          return userId;
        } catch (err) {
          return null;
        }
      }
      return null;
    },
  },
  created() {
    this.fetchThread();
    this.fetchComments();
  },
};
</script>
