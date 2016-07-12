package com.tsystems.ecare.app.dto;

import java.util.List;

/**
 * JSON serializable DTO containing data concerning a customers search request.
 */
public class CustomersDTO {

    private long currentPage;
    private long totalPages;

    List<CustomerDTO> customers;

    public CustomersDTO(long currentPage, long totalPages, List<CustomerDTO> customers) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.customers = customers;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<CustomerDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerDTO> customers) {
        this.customers = customers;
    }
}
