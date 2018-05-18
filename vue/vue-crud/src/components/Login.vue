<template>
    <div id="login">
        <el-form style="margin-top:20px" ref="form" :model="account" label-width="80px">
            <el-form-item label="账号">
                <el-input v-model="account.userName"></el-input>
            </el-form-item>
            <el-form-item label="密码">
                <el-input type="password" v-model="account.password"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
export default {
  name: 'Login',
  data(){
    return {
        account:{}
    }
  },
  methods: {
      onSubmit:function(){
          this.$http.post("/login",this.account).then(response =>{
              if(response.data.status == "success") {
                  var token = response.data.result;
                  //将token存放到浏览器的localStorage中
                  window.localStorage.setItem("jwtToken",token);

                  this.$message.success("登录成功");
                  this.$router.push("/home");
              } else {
                this.$message.error(response.data.message);    
              }
          }).catch(error=>{
              this.$message.error(error.message);
          });
      }
  }
}
</script>
<style lang="less" scoped>
    #login{
        width: 400px;
        margin: 0px auto;
    }
</style>
