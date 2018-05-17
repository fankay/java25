<template>
    <div id="addMovie">
        <el-button @click="backList">返回列表</el-button>

        <el-form style="margin-top:20px" ref="form" :model="movie" label-width="80px">
            <el-form-item label="电影名称">
                <el-input v-model="movie.title"></el-input>
            </el-form-item>
            <el-form-item label="评分">
                <el-input v-model="movie.rate"></el-input>
            </el-form-item>
            <el-form-item label="导演">
                <el-input v-model="movie.director"></el-input>
            </el-form-item>
            <el-form-item label="发行日期">
                <el-date-picker
                    v-model="movie.releaseYear"
                    type="date"
                    placeholder="发行日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="上映时间">
                <el-date-picker
                    v-model="movie.sendTime"
                    type="date"
                    placeholder="上映时间">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="简介">
                <el-input v-model="movie.description"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">立即创建</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import api from "../const/url"

export default {
  name: 'AddMovie',
  data(){
    return {
        movie:{}
    }
  },
  methods: {
      backList:function(){
          this.$router.push("/");
      },
      onSubmit:function(){
          this.$http.post(api.addMovie,this.movie).then(response => {
              if(response.data.status == "success") {
                  this.$message({
                    message: '新增成功',
                    type: 'success'
                  });
                  this.$router.push("/");
              } else {
                  this.$message.error(response.data.message);
              }
          }).catch(error => {
              this.$message.error("系统提示:" + error.message);
          });
      }
      
  }
}
</script>
<style lang="less" scoped>

</style>
