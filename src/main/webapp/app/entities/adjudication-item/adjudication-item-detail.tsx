import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './adjudication-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdjudicationItemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdjudicationItemDetail = (props: IAdjudicationItemDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { adjudicationItemEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adjudicationItemDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.detail.title">AdjudicationItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adjudicationItemEntity.id}</dd>
          <dt>
            <span id="outcome">
              <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.outcome">Outcome</Translate>
            </span>
          </dt>
          <dd>{adjudicationItemEntity.outcome}</dd>
          <dt>
            <span id="sequence">
              <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.sequence">Sequence</Translate>
            </span>
          </dt>
          <dd>{adjudicationItemEntity.sequence}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.claimResponse">Claim Response</Translate>
          </dt>
          <dd>{adjudicationItemEntity.claimResponse ? adjudicationItemEntity.claimResponse.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adjudication-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adjudication-item/${adjudicationItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ adjudicationItem }: IRootState) => ({
  adjudicationItemEntity: adjudicationItem.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdjudicationItemDetail);
