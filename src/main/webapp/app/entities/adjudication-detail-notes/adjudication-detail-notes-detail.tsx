import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './adjudication-detail-notes.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdjudicationDetailNotesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdjudicationDetailNotesDetail = (props: IAdjudicationDetailNotesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { adjudicationDetailNotesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adjudicationDetailNotesDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.adjudicationDetailNotes.detail.title">AdjudicationDetailNotes</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adjudicationDetailNotesEntity.id}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="hcpNphiesPortalApp.adjudicationDetailNotes.note">Note</Translate>
            </span>
          </dt>
          <dd>{adjudicationDetailNotesEntity.note}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.adjudicationDetailNotes.adjudicationDetailItem">Adjudication Detail Item</Translate>
          </dt>
          <dd>{adjudicationDetailNotesEntity.adjudicationDetailItem ? adjudicationDetailNotesEntity.adjudicationDetailItem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adjudication-detail-notes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adjudication-detail-notes/${adjudicationDetailNotesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ adjudicationDetailNotes }: IRootState) => ({
  adjudicationDetailNotesEntity: adjudicationDetailNotes.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjudicationDetailNotesDetail);
