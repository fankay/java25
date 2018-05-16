<template>
    <div id="news">
        <h3 class="title">今日新闻</h3>
        <ul class="item-group">
            <li class="news-title" v-for="item in news" :key="item.id">{{item.title}}</li>
        </ul>
    </div>
</template>
<script>
    export default {
        name: '',
        data(){
            return {
                news:[
                    {id:1,title:"new A"},
                    {id:2,title:"new B"},
                    {id:3,title:"new C"},
                ]
            }
        },
        methods: {
        }
    }
</script>
<style lang="less" scoped>
    #news{
        .title{
            color:#ff7400;
        }
        .item-group{
            .news-title{
                color:red;
            }
        }
    }
</style>
