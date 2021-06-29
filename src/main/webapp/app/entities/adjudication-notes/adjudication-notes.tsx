import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './adjudication-notes.reducer';
import { IAdjudicationNotes } from 'app/shared/model/adjudication-notes.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationNotes = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const adjudicationNotesList = useAppSelector(state => state.adjudicationNotes.entities);
  const loading = useAppSelector(state => state.adjudicationNotes.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="adjudication-notes-heading" data-cy="AdjudicationNotesHeading">
        <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.home.title">Adjudication Notes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.home.createLabel">Create new Adjudication Notes</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {adjudicationNotesList && adjudicationNotesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.note">Note</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.adjudicationItem">Adjudication Item</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {adjudicationNotesList.map((adjudicationNotes, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${adjudicationNotes.id}`} color="link" size="sm">
                      {adjudicationNotes.id}
                    </Button>
                  </td>
                  <td>{adjudicationNotes.note}</td>
                  <td>
                    {adjudicationNotes.adjudicationItem ? (
                      <Link to={`adjudication-item/${adjudicationNotes.adjudicationItem.id}`}>{adjudicationNotes.adjudicationItem.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${adjudicationNotes.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${adjudicationNotes.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${adjudicationNotes.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.home.notFound">No Adjudication Notes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default AdjudicationNotes;
