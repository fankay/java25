<template>
    <div id="home">
        <el-button type="primary" @click="addMovie">新增电影</el-button>
        <el-button @click="logout">安全退出</el-button>
        <el-table
        :data="movies"
        style="width: 100%">
            <el-table-column
                prop="title"
                label="电影名称">
            </el-table-column>
            <el-table-column
                prop="rate"
                label="评分"
                width="80">
            </el-table-column>
            <el-table-column
                prop="releaseYear"
                label="发行时间"
                width="100">
            </el-table-column>
            <el-table-column
                prop="sendTime"
                label="上映时间"
                width="100">
            </el-table-column>
            <el-table-column
                prop="director"
                label="导演">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button
                    size="mini"
                        @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button
                    size="mini"
                    type="danger"
                        @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            background
            layout="prev, pager, next,total"
            :total="total"
            :current-page="Number(currentPage)"
            @current-change="pageData">
        </el-pagination>
    </div>
</template>
<script>
import api from "../const/url"

export default {
  name: 'Home',
  data(){
    return {
        movies:[],
        total:0,
        currentPage:1
    }
  },
  methods: {
      addMovie:function(){
          this.$router.push("/new");
      },
      handleDelete:function(index,row){
          var id = row.id;
          this.$confirm("确定要删除该记录吗?").then(()=>{
              this.$http.delete("/movie/"+id).then(response=>{
                if(response.data.status == "success") {
                    this.$message.success("删除成功");
                    //this.$router.go(0);
                    //将数组中的内容删除
                    //this.movies.splice(index,1);
                    this.loadData(this.currentPage);
                }
              }).catch(error=>{
                this.$message.error("系统提示:" + error.message);
              });
          }).catch(()=>{});
      },
      handleEdit:function(index,row){
          var id = row.id;
          this.$router.push("/edit/"+id);
      },
      pageData:function(pageNo){
          this.currentPage = pageNo;
          this.loadData(this.currentPage);
      },
      loadData:function(pageNum){
          this.$http.get(api.movieHome+"/?p=" + pageNum).then(response => {
            this.movies = response.data.result.list;
            this.total = response.data.result.total;
          }).catch(error => {
            this.$message.error("系统提示:" + error.message);
          });
      },
      logout:function(){
          localStorage.removeItem("jwtToken");
          this.$message("你已安全退出");
          this.$router.push("/");
      }
  },
  mounted:function(){
      //this.currentPage = parseInt(this.$route.query.p);
      this.loadData(this.currentPage);
  }
}
</script>
<style lang="less" scoped>

</style>
