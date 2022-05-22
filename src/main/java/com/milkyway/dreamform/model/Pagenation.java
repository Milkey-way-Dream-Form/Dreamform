package com.milkyway.dreamform.model;

import com.milkyway.dreamform.dto.CommunityDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class Pagenation {
    private int pageSize = 10;
    private int blockSize = 10;
    private int page = 1;
    private int block = 1;
    private int totalListCnt;
    private int totalPageCnt;
    private int totalBlockCnt;
    private int startPage = 1;
    private int endPage = 1;
    private int prevBlock;
    private int nextBlock;


    public Pagenation(Page<CommunityDto> list, int page) {
        setPage(page);
        setTotalListCnt((int)list.getTotalElements());
        setTotalPageCnt((int)Math.ceil(totalListCnt * 1.0/ pageSize));
        setBlock((int) Math.ceil((page * 1.0)/blockSize));
        setStartPage((block - 1) * blockSize + 1);
        setEndPage(startPage + blockSize - 1);

        if(endPage > totalPageCnt) {
            this.endPage = totalPageCnt;
        }

        setPrevBlock((block * blockSize) - blockSize);

        if(prevBlock < 1) {
            this.prevBlock = 1;
        }

        setNextBlock((block * blockSize) + 1);

        if(nextBlock > totalPageCnt) {
            nextBlock = totalPageCnt;
        }

    }
}
