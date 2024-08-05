package com.itgroup.dao;

import com.itgroup.bean.Game;
import com.itgroup.utility.Paging;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GamaDao extends SuperDao{
    public GamaDao() {

    }

    public List<Game> sellectAll() {
        Connection conn = null;
        String sql = "select * from gameshopping order by gnum";
        PreparedStatement prsmt = null;
        ResultSet rs = null;
        List<Game> gameList = new ArrayList<>();
        try{
            conn = super.getConnection();
            prsmt = conn.prepareStatement(sql);
            rs = prsmt.executeQuery();


            while (rs.next()){
                Game bean = this.makeBean(rs);

                gameList.add(bean);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                if(rs != null) {rs.close();}
                if(prsmt != null){prsmt.close();}
                if(conn != null){conn.close();}
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return gameList;
    }

    private Game makeBean(ResultSet rs) {
        Game bean = new Game();
        try {
            bean.setGnum(rs.getInt("gnum"));
            bean.setGname(rs.getString("gname"));
            bean.setGgenre(rs.getString("ggenre"));
            bean.setImage01(rs.getString("image01"));
            bean.setImage02(rs.getString("image02"));
            bean.setCompany(rs.getString("company"));
            bean.setPrice(rs.getInt("price"));
            bean.setReldate(String.valueOf(rs.getDate("reldate")));
            bean.setContent(rs.getString("content"));
            bean.setCount(rs.getInt("count"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return bean;
    }

    public int InsertGame(Game insGame) {
        int cnt = -1;
        Connection conn = null;
        String sql = "insert into gameshopping(gnum,gname,ggenre,image01,image02,company,price,reldate,content, count)";
        sql += " values(seqgnum.nextval,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prsmt = null;
        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            prsmt = conn.prepareStatement(sql);
            prsmt.setString(1,insGame.getGname());
            prsmt.setString(2,insGame.getGgenre());
            prsmt.setString(3,insGame.getImage01());
            prsmt.setString(4,insGame.getImage02());
            prsmt.setString(5,insGame.getCompany());
            prsmt.setInt(6,insGame.getPrice());
            prsmt.setString(7,insGame.getReldate());
            prsmt.setString(8,insGame.getContent());
            prsmt.setInt(9,insGame.getCount());
            cnt = prsmt.executeUpdate();
            conn.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            try {
                conn.rollback();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }finally {
            try {
                if(prsmt != null){prsmt.close();}
                if(conn != null){conn.close();}
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return cnt;
    }

    public int updateGame(Game upGame) {
        int cnt = -1;
        Connection conn = null;
        String sql = "update gameshopping set gname = ?, ggenre = ?, image01 = ?, image02 = ?, company = ?, price =?, reldate = ?, content = ?, count = ?";
        sql += " where gnum = ?";
        PreparedStatement prsmt = null;

        try {
            conn = super.getConnection();
            prsmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            prsmt.setString(1, upGame.getGname());
            prsmt.setString(2, upGame.getGgenre());
            prsmt.setString(3, upGame.getImage01());
            prsmt.setString(4, upGame.getImage02());
            prsmt.setString(5, upGame.getCompany());
            prsmt.setInt(6, upGame.getPrice());
            prsmt.setString(7,upGame.getReldate());
            prsmt.setString(8,upGame.getContent());
            prsmt.setInt(9, upGame.getCount());
            prsmt.setInt(10, upGame.getGnum());
            cnt = prsmt.executeUpdate();
            conn.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            try {
                conn.rollback();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }finally {
            try {
                if(prsmt != null){prsmt.close();}
                if(conn != null){conn.close();}
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return cnt;
    }

    public List<Game> SelectGgenreGame(String ggenre) {
        Connection conn = null;
        String sql = "select * from gameshopping ";
        boolean bool = (ggenre == null) || (ggenre.equals("all"));
        if (!bool) {
            sql += " where ggenre Like \'%" + ggenre + "%\'";
        }
        PreparedStatement prsmt = null;
        ResultSet rs = null;
        List<Game> gameList = new ArrayList<>();

        try {
            conn = super.getConnection();
            prsmt = conn.prepareStatement(sql);
            rs = prsmt.executeQuery();

            while (rs.next()) {
                Game bean = this.makeBean(rs);
                gameList.add(bean);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prsmt != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return gameList;
    }

    public int getTotalCount(String ggenre) {
        int count = 0;
        Connection conn = null;
        String sql ="select count(*) as mycnt from gameshopping";
        boolean bool = (ggenre == null) || (ggenre.equals("all"));
        if(!bool){
            sql += " where ggenre Like \'%" + ggenre + "%\'";
        }
        PreparedStatement prsmt = null;
        ResultSet rs = null;

        try {
            conn = super.getConnection();
            prsmt = conn.prepareStatement(sql);
            rs = prsmt.executeQuery();

            if(rs.next()){
                count = rs.getInt("mycnt");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                if(rs != null){rs.close();}
                if(prsmt != null){prsmt.close();}
                if(conn != null){conn.close();}
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return count;
    }

    public List<Game> getPaginationData(Paging pageInfo) {
        Connection conn = null;
        String sql = "select gnum, gname, ggenre, image01, image02, company, price, reldate, content, count";
        sql += " from ( ";
        sql +=" select gnum, gname, ggenre, image01, image02, company, price, reldate, content, count,";
        sql +=" rank() over(order by gnum desc) as ranking";
        sql +=" from gameshopping";
        String mode = pageInfo.getMode();
        boolean bool = pageInfo.equals(null) || pageInfo.equals("null") || mode.equals("")||mode.equals("all");
        if(!bool){
            sql += " where ggenre Like \'%" + mode + "%\'";
        }
        sql +=" ) ";
        sql +=" where ranking between ? and ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Game> allData = new ArrayList<>();

        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            if(!bool) {
                pstmt.setInt(1, pageInfo.getBeginRow());
                pstmt.setInt(2,pageInfo.getEndRow());
            }else {
                pstmt.setInt(1, pageInfo.getBeginRow());
                pstmt.setInt(2,pageInfo.getEndRow());
            }

            rs = pstmt.executeQuery();

            while (rs.next()){
                Game bean = this.makeBean(rs);

                allData.add(bean);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(rs != null){rs.close();}
                if(pstmt != null){pstmt.close();}
                if(conn != null){conn.close();}
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return allData;
    }

    public int deleteGame(int gnum) {
        Connection conn = null;
        String sql = "delete from gameshopping where gnum = ?";
        PreparedStatement pstmt = null;
        int cnt = -1;

        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,gnum);
            cnt = pstmt.executeUpdate();
            conn.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            try {
                conn.rollback();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }finally {
            try {
                if(pstmt != null){pstmt.close();}
                if(conn != null){conn.close();}
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return cnt;
    }
}
