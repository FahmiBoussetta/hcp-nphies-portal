import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './adjudication-notes.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdjudicationNotesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdjudicationNotesDetail = (props: IAdjudicationNotesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { adjudicationNotesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adjudicationNotesDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.detail.title">AdjudicationNotes</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adjudicationNotesEntity.id}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.note">Note</Translate>
            </span>
          </dt>
          <dd>{adjudicationNotesEntity.note}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.adjudicationNotes.adjudicationItem">Adjudication Item</Translate>
          </dt>
          <dd>{adjudicationNotesEntity.adjudicationItem ? adjudicationNotesEntity.adjudicationItem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adjudication-notes" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adjudication-notes/${adjudicationNotesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ adjudicationNotes }: IRootState) => ({
  adjudicationNotesEntity: adjudicationNotes.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjudicationNotesDetail);
