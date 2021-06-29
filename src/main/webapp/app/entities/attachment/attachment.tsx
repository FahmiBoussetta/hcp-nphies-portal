import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './attachment.reducer';
import { IAttachment } from 'app/shared/model/attachment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Attachment = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const attachmentList = useAppSelector(state => state.attachment.entities);
  const loading = useAppSelector(state => state.attachment.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="attachment-heading" data-cy="AttachmentHeading">
        <Translate contentKey="hcpNphiesPortalApp.attachment.home.title">Attachments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.attachment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.attachment.home.createLabel">Create new Attachment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {attachmentList && attachmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.attachment.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.attachment.contentType">Content Type</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.attachment.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.attachment.language">Language</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.attachment.isData">Is Data</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.attachment.dataFile">Data File</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.attachment.url">Url</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.attachment.attachmentSize">Attachment Size</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.attachment.hash">Hash</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {attachmentList.map((attachment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${attachment.id}`} color="link" size="sm">
                      {attachment.id}
                    </Button>
                  </td>
                  <td>{attachment.contentType}</td>
                  <td>{attachment.title}</td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.LanguageEnum.${attachment.language}`} />
                  </td>
                  <td>{attachment.isData ? 'true' : 'false'}</td>
                  <td>
                    {attachment.dataFile ? (
                      <div>
                        {attachment.dataFileContentType ? (
                          <a onClick={openFile(attachment.dataFileContentType, attachment.dataFile)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {attachment.dataFileContentType}, {byteSize(attachment.dataFile)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{attachment.url}</td>
                  <td>{attachment.attachmentSize}</td>
                  <td>
                    {attachment.hash ? (
                      <div>
                        {attachment.hashContentType ? (
                          <a onClick={openFile(attachment.hashContentType, attachment.hash)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {attachment.hashContentType}, {byteSize(attachment.hash)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${attachment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${attachment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${attachment.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="hcpNphiesPortalApp.attachment.home.notFound">No Attachments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Attachment;
