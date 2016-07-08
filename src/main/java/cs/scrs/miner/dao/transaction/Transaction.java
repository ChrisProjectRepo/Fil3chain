/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package cs.scrs.miner.dao.transaction;


import cs.scrs.miner.dao.citations.Citation;
import cs.scrs.miner.dao.user.User;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;



@Entity
@Table(name = "transaction")
public class Transaction {

	// Columns
	@Id
	@Column(name = "hashFile")
	private String hashFile;
	@Column(name = "filename")
	private String filename;
	@Column(name = "index_in_block")
	private Integer indexInBlock;

	// Relations
	@Column(name = "blockContainer")
	private String blockContainer;

	@ManyToOne
	@JoinColumn(name = "User_publicKeyHash")
	private User authorContainer;

	// @ManyToMany
	// @JoinTable(name = "Citations", joinColumns = { @JoinColumn(name = "Transaction_hashFileCite", referencedColumnName = "hashFile") }, inverseJoinColumns = { @JoinColumn(name = "Transaction_hashFileCited", referencedColumnName = "hashFile") })
	// private List<Transaction> citationsContainer;

	@OneToMany
	@JoinColumn(name = "hashCiting")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<Citation> citationsContainer;


	public Transaction(String hashFile, String filename) {
		super();
		this.hashFile = hashFile;
		this.filename = filename;
	}

	public Transaction() {
	}

	/**
	 * @return the hashFile
	 */
	public String getHashFile() {

		return hashFile;
	}

	/**
	 * @param hashFile
	 *            the hashFile to set
	 */
	public void setHashFile(String hashFile) {

		this.hashFile = hashFile;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {

		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename) {

		this.filename = filename;
	}

	/**
	 * @return the blockContainer
	 */
	public String getBlockContainer() {

		return blockContainer;
	}

	/**
	 * @param blockContainer
	 *            the blockContainer to set
	 */
	public void setBlockContainer(String blockContainer) {

		this.blockContainer = blockContainer;
	}

	/**
	 * @return the authorContainer
	 */
	public User getAuthorContainer() {

		return authorContainer;
	}

	/**
	 * @param authorContainer
	 *            the authorContainer to set
	 */
	public void setAuthorContainer(User authorContainer) {

		this.authorContainer = authorContainer;
	}

	/**
	 * @return the citationsContainer
	 */
	public List<Citation> getCitationsContainer() {

		return citationsContainer;
	}

	/**
	 * @param citationsContainer
	 *            the citationsContainer to set
	 */
	public void setCitationsContainer(List<Citation> citationsContainer) {

		this.citationsContainer = citationsContainer;
	}

	public Integer getIndexInBlock() {

		return indexInBlock;
	}

	public void setIndexInBlock(Integer indexInBlock) {

		this.indexInBlock = indexInBlock;
	}

	@Override
	public String toString() {

		return "{\"" + "hashFile\":\"" + hashFile + "\"" + "," + "\"filename\": \"" + filename + "\"" + "," + "\"indexInBlock\":\"" + indexInBlock + "\"," + "\"blockContainer\": \"" + blockContainer + "\"" + "," + "\"authorContainer\": "
				+ authorContainer + "," + "\"citationsContainer\": " + citationsContainer + "}";
	}
}