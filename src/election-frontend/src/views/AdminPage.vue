<template>
  <div>
    <h1>User Management</h1>

<!--    &lt;!&ndash; Form to add/edit users &ndash;&gt;-->
<!--    <form @submit.prevent="submitForm">-->
<!--      <input type="hidden" v-model="user.id" />-->
<!--      <label>Username: <input type="text" v-model="user.username" /></label>-->
<!--      <label>Email: <input type="email" v-model="user.email" /></label>-->
<!--      <label>Password: <input type="password" v-model="user.password" /></label>-->
<!--     -->
<!--      <button type="submit">Save</button>-->
<!--    </form>-->


    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Email</th>
        <th>Active</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id">
        <td>{{ user.id }}</td>
        <td>{{ user.username }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.active ? 'Yes' : 'No' }}</td>
        <td>
<!--          <button @click="editUser(user)">Edit</button>-->
          <button @click="deleteUser(user.id)">Delete</button>
          <button @click="toggleActive(user)">
            {{ user.active ? 'Disable' : 'Enable' }}
          </button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

export default {
  data() {
    return {
      users: [],
      user: { id: null, username: '', email: '', password: '', active: false }
    };
  },
  created() {
    this.loadUsers();
  },
  methods: {

    async loadUsers() {
      try {
        const response = await fetch('/admin/users');
        this.users = await response.json();
      } catch (error) {
        console.error("Error loading users:", error);
      }
    },


    // Delete user with confirmation
    async deleteUser(id) {
      if (confirm('Are you sure you want to delete this user?')) {
        try {
          const response = await fetch(`/admin/users/${id}`, {
            method: 'DELETE',
          });
          if (response.ok) {
            await this.loadUsers();
          } else {
            console.error('Failed to delete user:', response.statusText);
          }
        } catch (error) {
          console.error("Error deleting user:", error);
        }
      }
    },

    // Toggle user active status (Enable/Disable)
    async toggleActive(user) {
      const action = user.active ? 'Disable' : 'Enable';
      if (confirm(`Are you sure you want to ${action} this user?`)) {
        try {
          const response = await fetch(`/admin/users/${user.id}/toggle-active`, {
            method: 'PUT',
          });
          if (response.ok) {
            await this.loadUsers();
          } else {
            console.error(`Failed to ${action.toLowerCase()} user:`, response.statusText);
          }
        } catch (error) {
          console.error(`Error trying to ${action.toLowerCase()} user:`, error);
        }
      }
    },


    // resetForm() {
    //   this.user = {id: null, username: '', email: '', password: '', active: false};
    // }
  }
};
</script>
