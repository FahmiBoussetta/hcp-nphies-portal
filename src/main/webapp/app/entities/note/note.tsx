import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './note.reducer';
import { INote } from 'app/shared/model/note.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Note = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const noteList = useAppSelector(state => state.note.entities);
  const loading = useAppSelector(state => state.note.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="note-heading" data-cy="NoteHeading">
        <Translate contentKey="hcpNphiesPortalApp.note.home.title">Notes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.note.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.note.home.createLabel">Create new Note</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {noteList && noteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.note.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.note.text">Text</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.note.author">Author</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.note.time">Time</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.note.communication">Communication</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.note.communicationRequest">Communication Request</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {noteList.map((note, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${note.id}`} color="link" size="sm">
                      {note.id}
                    </Button>
                  </td>
                  <td>{note.text}</td>
                  <td>{note.author}</td>
                  <td>{note.time ? <TextFormat type="date" value={note.time} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{note.communication ? <Link to={`communication/${note.communication.id}`}>{note.communication.id}</Link> : ''}</td>
                  <td>
                    {note.communicationRequest ? (
                      <Link to={`communication-request/${note.communicationRequest.id}`}>{note.communicationRequest.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${note.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${note.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${note.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hcpNphiesPortalApp.note.home.notFound">No Notes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Note;
